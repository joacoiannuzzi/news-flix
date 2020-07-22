import React from "react";
import {Container, Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {changePassword} from "../util/APIUtils";
import useFormInput from "../components/hooks/useFormInput";
import {useUser} from "../App";

const ChangePassword = ({history}) => {
    const {currentUser: user} = useUser();
    const password = useFormInput('');

    const handleSubmit = event => {
        event.preventDefault();
        const changePasswordRequest = {
            user,
            password: password.value
        };

        changePassword(changePasswordRequest)
            .then(response => history.push("/login"))
            .catch(error => {
                alert('Invalid Request');
                console.log(error);
            })
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


                <Button variant="primary" type="submit">
                    Submit
                </Button>

            </Form>
        </Container>
    </>

};

export default ChangePassword