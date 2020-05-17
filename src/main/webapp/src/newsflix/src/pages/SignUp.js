import React, {Component} from "react";
import {Container, Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {checkEmailAvailability, signup} from "../util/APIUtils";

class SignUp extends Component {

    constructor(props) {
        super(props);
        this.state = {
            firstName: '',
            lastName: '',
            email: '',
            password: '',
        }
    }

    handleChange = event => {
        const isCheckbox = event.target.type === 'checkbox';
        this.setState({
            [event.target.name]: isCheckbox ?
                event.target.checked :
                event.target.value
        })
    };

    handleSubmit = event => {
        event.preventDefault();
        const signupRequest = this.state;
        let isEmailAvailable = false
        checkEmailAvailability(signupRequest.email)
            .then(response => {
                if (response.available) {
                    isEmailAvailable = true
                }
            }).catch(error => {
            console.log('the email is already available')
        })
        if (isEmailAvailable) {
            signup(signupRequest)
                .then(response => this.props.history.push("/login"))
                .catch(error => console.log(error))
        }

    };

    render() {
        return <>
            <h1 className="display-4 text-center">Registrate</h1>

            <Container>
                <Form onSubmit={this.handleSubmit}>

                    <Form.Group controlId="firstName">
                        <Form.Label>Nombre</Form.Label>
                        <Form.Control onChange={this.handleChange} name="firstName" type="text" placeholder="Nombre"
                                      required/>
                    </Form.Group>

                    <Form.Group controlId="lastName">
                        <Form.Label>Apellido</Form.Label>
                        <Form.Control onChange={this.handleChange} name="lastName" type="text" placeholder="Apellido"
                                      required/>
                    </Form.Group>

                    <Form.Group controlId="email">
                        <Form.Label>Correo electronico</Form.Label>
                        <Form.Control onChange={this.handleChange} name="email" type="email"
                                      placeholder="Correo electronico" required/>
                    </Form.Group>

                    <Form.Group controlId="password">
                        <Form.Label>Contraseña</Form.Label>
                        <Form.Control onChange={this.handleChange} name="password" type="password"
                                      placeholder="Contraseña" required/>
                    </Form.Group>

                    <Button variant="primary" type="submit">
                        Submit
                    </Button>
                </Form>
            </Container>
        </>
    }

}

export default SignUp