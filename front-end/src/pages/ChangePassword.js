import React from "react";
import {Container, Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {changePassword, getCurrentUser} from "../util/APIUtils";
import useFormInput from "../components/hooks/useFormInput";

const ChangePassword = ({history}) => {
    const user = getCurrentUser();
    const password = useFormInput('');

    const handleSubmit = event => {
        event.preventDefault();
        const changePasswordRequest = {
            user: user.value,
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