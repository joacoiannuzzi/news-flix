import React from "react";
import {Container, Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {changePassword} from "../util/APIUtils";
import useFormInput from "../components/hooks/useFormInput";
import {useUser} from "../App";

const ChangePassword = ({history}) => {
    const {currentUser: {id: userId}} = useUser();
    const password = useFormInput('');
    const password2 = useFormInput('');

    const handleSubmit = event => {
        event.preventDefault();
        if (password.value===password2.value){

        const changePasswordRequest = {
            userId,
            password: password.value
        };

        changePassword(changePasswordRequest)
            .then(response => {
                alert("Success please re-log");
                history.push("/login");
                })
            .catch(error => {
                alert('Invalid Request');
                console.log(error);
            })
        }else(
            alert('Put same password')
        )
    };



    return <>
        <h1 className="display-4 text-center">Cambia tu Contraseña</h1>

        <Container>
            <Form onSubmit={handleSubmit}>

                <Form.Group controlId="password">
                    <Form.Label>Contraseña</Form.Label>
                    <Form.Control name="password" type="password"
                                  placeholder="Contraseña" required
                                  {...password}
                    />
                </Form.Group>

                <Form.Group controlId="passwordCheck">
                    <Form.Label>Reintroduzca su contraseña</Form.Label>
                    <Form.Control name="password2" type="password"
                                  placeholder="Contraseña" required
                                  {...password2}
                    />
                </Form.Group>


                <Button variant="primary" type="submit">
                    Submit
                </Button>

            </Form>
        </Container>
    </>

};

export default ChangePassword