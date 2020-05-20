import React, {Component} from 'react';
import {Button, Form, FormControl, Nav, Navbar, NavDropdown} from "react-bootstrap";
import Dropdown from "react-bootstrap/Dropdown";
import {getCategories, getNewspapers} from "../util/APIUtils";
import {Link, withRouter} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUser} from '@fortawesome/free-solid-svg-icons'

class AppNav extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            categories: [],
            newspapers: [],
            search: ''
        }
    }

    handleLogout = () => {
        this.props.onLogout()
    };

    handleSearchSubmit = event => {
        event.preventDefault()
        const {search} = this.state
        this.props.history.push(`/search?query=${search}`)
    }

    handleSearchChange = event => {
        const {name, value} = event.target
        this.setState({
            [name]: value
        })
    }

    componentDidMount() {
        getCategories()
            .then(response => {
                this.setState({
                    categories: response
                })
            }).catch(error => console.log(error))

        getNewspapers()
            .then(response => {
                this.setState({
                    newspapers: response
                })
            }).catch(error => console.log(error))
    }

    render() {

        if (!this.props.isAuthenticated) {
            return <></>
        }

        const {categories, newspapers} = this.state;

        let newspapersSection = newspapers.map(newspaper => {
            return (
                <NavDropdown.Item as={Link} to={`/newspapers/${newspaper}`} key={newspaper}>
                    {newspaper}
                </NavDropdown.Item>
            )
        });

        let categoriesSection = categories.map(category => {
            return (
                <NavDropdown.Item as={Link} to={`/categories/${category}`} key={category}>
                    {category}
                </NavDropdown.Item>
            )
        });

        return (
            <div>
                <Navbar bg="dark" variant="dark" expand="md">
                    <Navbar.Brand as={Link} to={'/'}>
                        {/*<img*/}
                        {/*    src={'logo'}*/}
                        {/*/>*/}
                        NewsFlix
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                    <Navbar.Collapse>
                        <Nav className="mr-auto">
                            <NavDropdown title="Categorias">
                                {categoriesSection}
                            </NavDropdown>
                            <NavDropdown title="Diarios">
                                {newspapersSection}
                            </NavDropdown>
                        </Nav>
                        <Nav>
                            <Dropdown as={Nav.Item}>
                                <Dropdown.Toggle as={Nav.Link}>
                                    <FontAwesomeIcon icon={faUser}/>
                                </Dropdown.Toggle>
                                <Dropdown.Menu>
                                    <Dropdown.Item disabled>
                                        {this.props.currentUser.firstName} {this.props.currentUser.lastName}
                                    </Dropdown.Item>
                                    <Dropdown.Divider/>
                                    <Dropdown.Item as={Link} to="/profile">Mi perfil</Dropdown.Item>
                                    <Dropdown.Item as={Link} to="/favorites">Mis Favoritos</Dropdown.Item>
                                    <Dropdown.Item onClick={this.handleLogout}>
                                        Cerrar sesion
                                    </Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>
                        </Nav>
                        <Form inline onSubmit={this.handleSearchSubmit}>
                            <FormControl type="text" name='search' placeholder="Buscar" className="mr-sm-2" onChange={this.handleSearchChange}/>
                            <Button type={"submit"} variant="outline-success">Buscar</Button>
                        </Form>
                    </Navbar.Collapse>
                </Navbar>

            </div>
        );
    }
}

export default withRouter(AppNav);