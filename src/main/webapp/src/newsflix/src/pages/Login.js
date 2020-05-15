import React, {Component} from "react";
import {Container, Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

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

    handleSubmit = async event => {
        event.preventDefault();
        const user = this.state

        const requestOptions = {
            method: 'Post',
            // mode: 'no-cors',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json',
            },
            body: JSON.stringify(user)
        };


        await fetch("/api/auth/login", requestOptions)
            .then(response => {
                console.log(response)
                this.props.history.push("/")
                //TODO; make it log... etc.
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