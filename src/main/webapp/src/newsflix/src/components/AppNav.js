import React, {Component, useState} from 'react';
import {Button, Form, FormControl, Nav, Navbar, NavDropdown} from "react-bootstrap";
import Dropdown from "react-bootstrap/Dropdown";

class AppNav extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            categories: [],
            newspapers: []
        }
    }


    async componentDidMount() {

        const response = await fetch('/api/articles/categories/all', {
            method: 'GET',
            headers: {
                'content-type': 'application/json',
                'Accept': 'application/json'
            }
        });
        try {
            const body = await response.json();
            this.setState({categories: body});
        } catch (e) {
        }


        const response2 = await fetch('/api/articles/newspapers/all', {
            method: 'GET',
            headers: {
                'content-type': 'application/json',
                'Accept': 'application/json'
            }
        });
        try {
            const body2 = await response2.json();
            this.setState({newspapers: body2, isLoading: false});
        } catch (e) {

        }
    }

    render() {

        const {categories, newspapers} = this.state;

        let newspapersSection = newspapers.map(newspaper => {
            return (
                <NavDropdown.Item key={newspaper} href={`/newspapers/${newspaper}`}>{newspaper}</NavDropdown.Item>
            )
        });

        let categoriesSection = categories.map(category => {
            return (
                <NavDropdown.Item key={category} href={`/categories/${category}`}>{category}</NavDropdown.Item>
            )
        });

        return (
            <div>
                <Navbar bg="dark" variant="dark" expand="md">
                    <Navbar.Brand href="/">NewsFlix</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="mr-auto">
                            <NavDropdown title="Categorias" id="basic-nav-dropdown">
                                {categoriesSection}
                            </NavDropdown>
                            <NavDropdown title="Diarios" id="basic-nav-dropdown">
                                {newspapersSection}
                            </NavDropdown>
                        </Nav>
                        <Dropdown alignRight>
                                <Dropdown.Toggle variant="success" id="dropdown-basic">
                                    Acciones
                                </Dropdown.Toggle>
                                <Dropdown.Menu>
                                    <Dropdown.Item href="#/profile">Mi perfil</Dropdown.Item>
                                    <Dropdown.Item href="#/favorites">Mis Favoritos</Dropdown.Item>
                                    <Dropdown.Item href="#/logout">Logout</Dropdown.Item>
                                </Dropdown.Menu>
                        </Dropdown>

                        <Form inline>
                            <FormControl type="text" placeholder="Search" className="mr-sm-2"/>
                            <Button variant="outline-success">Search</Button>
                        </Form>
                    </Navbar.Collapse>
                </Navbar>

            </div>
        );
    }
}

export default AppNav;