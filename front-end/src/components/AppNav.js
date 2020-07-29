import React from 'react';
import {Button, Form, Nav, Navbar, NavDropdown, NavLink} from "react-bootstrap";
import Dropdown from "react-bootstrap/Dropdown";
import {getCategories, getNewspapers} from "../util/APIUtils";
import {Link, withRouter} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUser} from '@fortawesome/free-solid-svg-icons'
import {useUser} from "../App";
import useSection from "./hooks/useSection";


const makeDropdownMenu = (list, name) => (
    list.map(item =>
        (
            <NavDropdown.Item as={Link} to={`/${name}/${item}`} key={item}>
                {item}
            </NavDropdown.Item>
        )
    )
);

const AppNav = ({isAuthenticated, onLogout, history}) => {
    const {currentUser: user} = useUser();
    const categories = useSection(getCategories);
    const newspapers = useSection(getNewspapers);

    if (!isAuthenticated) {
        return <></>
    }

    const handleSearchSubmit = event => {
        event.preventDefault();
        history.push(`/search`)
    };

    const newspapersSection = makeDropdownMenu(newspapers, 'newspapers');
    const categoriesSection = makeDropdownMenu(categories, 'categories');

    return (
        <>
            <Navbar bg="dark" variant="dark" expand="md">
                <Navbar.Brand as={Link} to={'/'}>
                    NewsFlix
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse>
                    <Nav className="mr-auto">
                        <NavDropdown title="Categorias" id={categories}>
                            {categoriesSection}
                        </NavDropdown>
                        <NavDropdown title="Diarios" id={newspapers}>
                            {newspapersSection}
                        </NavDropdown>
                        <NavLink as={Link} to={'/favorites'}>
                            Mis Favoritos
                        </NavLink>
                    </Nav>
                    <Nav>
                        <Dropdown as={Nav.Item}>
                            <Dropdown.Toggle as={Nav.Link}>
                                <FontAwesomeIcon icon={faUser}/>
                            </Dropdown.Toggle>
                            <Dropdown.Menu>
                                <Dropdown.Item disabled>
                                    {user.firstName} {user.lastName}
                                </Dropdown.Item>
                                <Dropdown.Divider/>
                                <Dropdown.Item as={Link} to="/profile">Mi perfil</Dropdown.Item>
                                <Dropdown.Item as={Link} to="/favorites">Mis Favoritos</Dropdown.Item>
                                <Dropdown.Item onClick={onLogout}>
                                    Cerrar sesion
                                </Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>
                    </Nav>
                    <Form inline onSubmit={handleSearchSubmit}>
                        <Button type={"submit"} variant="outline-success">Buscar</Button>
                    </Form>
                </Navbar.Collapse>
            </Navbar>
        </>
    );

}


export default withRouter(AppNav);

