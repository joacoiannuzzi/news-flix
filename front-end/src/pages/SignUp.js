import React from "react";
import {Container, Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {signup} from "../util/APIUtils";
import {Link} from "react-router-dom";
import useFormInput from "../components/hooks/useFormInput";

const SignUp = ({history}) => {
    const firstName = useFormInput('');
    const lastName = useFormInput('');
    const email = useFormInput('');
    const password = useFormInput('');

    const handleSubmit = event => {
        event.preventDefault();
        const signUpRequest = {
            firstName: firstName.value,
            lastName: lastName.value,
            email: email.value,
            password: password.value
        };

        signup(signUpRequest)
            .then(response => history.push("/login"))
            .catch(error => {
                alert('Invalid Request');
                console.log(error);
            })
    };

    return <>
        <h1 className="display-4 text-center">Registrate</h1>

        <Container>
            <Form onSubmit={handleSubmit}>

                <Form.Group controlId="firstName">
                    <Form.Label>Nombre</Form.Label>
                    <Form.Control name="firstName" type="text" placeholder="Nombre"
                                  required
                                  {...firstName}
                    />
                </Form.Group>

                <Form.Group controlId="lastName">
                    <Form.Label>Apellido</Form.Label>
                    <Form.Control name="lastName" type="text" placeholder="Apellido"
                                  required
                                  {...lastName}
                    />
                </Form.Group>

                <Form.Group controlId="email">
                    <Form.Label>Correo electronico</Form.Label>
                    <Form.Control name="email" type="email"
                                  placeholder="Correo electronico" required
                                  {...email}
                    />
                </Form.Group>

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

                <Link to={'/login'} className='ml-3'>Iniciar sesion</Link>
            </Form>
        </Container>
    </>

};

export default SignUp