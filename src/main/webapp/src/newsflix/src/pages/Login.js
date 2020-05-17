import React, {Component} from "react";
import {Container, Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {login} from "../util/APIUtils";
import {ACCESS_TOKEN} from "../constants";

class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
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
        const loginRequest = this.state
        login(loginRequest)
            .then(response => {
                localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                this.props.onLogin();
            }).catch(error => {
            console.log(error)
        })


    };

    render() {
        return (
            <>
                <Container>
                    <h1 className="display-4 text-center">Inicia sesión</h1>

                    <Form onSubmit={this.handleSubmit}>

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
        )
    }

}

export default Login