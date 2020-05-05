import React, {Component} from "react";
import {Container, Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

class SignUp extends Component {

    constructor(props) {
        super(props);
        this.state = {
            firstName: '',
            lastName: '',
            email: '',
            password: '',
            isActive: true
        }
    }

    handleChange = event => {
        const isCheckbox = event.target.type === 'checkbox'
        this.setState({
            [event.target.name]: isCheckbox ?
                event.target.checked :
                event.target.value
        })
    }

    handleSubmit = async event => {
        event.preventDefault()
        const user = this.state

        let response = await fetch("/api/users/save", {
            method: 'post',
            mode: 'no-cors',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json',
            },
            body: JSON.stringify(user)
        });
        console.log(response)
    }

    render() {
        return <>
            <h1 className="display-4 text-center">Registrate</h1>

            <Container>
                <Form onSubmit={this.handleSubmit}>

                    <Form.Group controlId="firstName">
                        <Form.Label>Nombre</Form.Label>
                        <Form.Control onChange={this.handleChange} name="firstName" type="text" placeholder="Nombre"/>
                    </Form.Group>

                    <Form.Group controlId="lastName">
                        <Form.Label>Apellido</Form.Label>
                        <Form.Control onChange={this.handleChange} name="lastName" type="text" placeholder="Apellido"/>
                    </Form.Group>

                    <Form.Group controlId="email">
                        <Form.Label>Correo electronico</Form.Label>
                        <Form.Control onChange={this.handleChange} name="email" type="email"
                                      placeholder="Correo electronico"/>
                    </Form.Group>

                    <Form.Group controlId="password">
                        <Form.Label>Contraseña</Form.Label>
                        <Form.Control onChange={this.handleChange} name="password" type="password"
                                      placeholder="Contraseña"/>
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