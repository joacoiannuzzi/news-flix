import React, {Component} from 'react';
import {Button, Form, FormControl, Nav, Navbar, NavDropdown} from "react-bootstrap";
import Dropdown from "react-bootstrap/Dropdown";
import {getCategories, getNewspapers} from "../util/APIUtils";
import {withRouter} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUser} from '@fortawesome/free-solid-svg-icons'

class AppNav extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            categories: [],
            newspapers: []
        }
    }

    handleLogout = () => {
        this.props.onLogout()
    };


    componentDidMount() {

        getCategories()
            .then(response => {
                this.setState({
                    categories: response
                })
            }).catch(error => {

        })
        getNewspapers()
            .then(response => {
                this.setState({
                    newspapers: response
                })
            }).catch(error => {

        })
    }

    render() {

        if (!this.props.currentUser) {
            return <></>
        }

        const {categories, newspapers} = this.state;

        let newspapersSection = newspapers.map(newspaper => {
            return (
                <NavDropdown.Item key={newspaper} href={`/newspapers/${newspaper}`}>
                    {newspaper}
                </NavDropdown.Item>
            )
        });

        let categoriesSection = categories.map(category => {
            return (
                <NavDropdown.Item key={category} href={`/categories/${category}`}>
                    {category}
                </NavDropdown.Item>
            )
        });

        return (
            <div>
                <Navbar bg="dark" variant="dark" expand="md">
                    <Navbar.Brand href="/">
                        {/*<img*/}
                        {/*    src={'logo'}*/}
                        {/*/>*/}
                        NewsFlix
                    </Navbar.Brand>
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
                        <Form inline>
                            <FormControl type="text" placeholder="Search" className="mr-sm-2"/>
                            <Button variant="outline-success">Search</Button>
                        </Form>
                        <Nav>
                            <Dropdown as={Nav.Item}>
                                <Dropdown.Toggle as={Nav.Link}>
                                    <FontAwesomeIcon icon={faUser} />
                                </Dropdown.Toggle>
                                <Dropdown.Menu>
                                    <Dropdown.Item href="#/profile">Mi perfil</Dropdown.Item>
                                    <Dropdown.Item href="#/favorites">Mis Favoritos</Dropdown.Item>
                                    <Dropdown.Item onClick={this.handleLogout}>
                                        Logout
                                    </Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>

            </div>
        );
    }
}

export default withRouter(AppNav);