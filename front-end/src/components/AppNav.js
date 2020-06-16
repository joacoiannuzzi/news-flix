import React, {useState, useEffect} from 'react';
import {Button, Form, FormControl, Nav, Navbar, NavDropdown, NavLink} from "react-bootstrap";
import Dropdown from "react-bootstrap/Dropdown";
import {getCategories, getNewspapers} from "../util/APIUtils";
import {Link, withRouter} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUser} from '@fortawesome/free-solid-svg-icons'
import useFormInput from './hooks/useFormInput';
import {useUser} from "../App";


const makeDropdownMenu = (list, name) => (
    list.map(item =>
        (
            <NavDropdown.Item as={Link} to={`/${name}/${item}`} key={item}>
                {item}
            </NavDropdown.Item>
        )
    )
)

const useSection = callback => {
    const [section, setSection] = useState([])
    useEffect(
        () => {
            callback()
                .then(response => {
                    setSection(response)
                })
                .catch(error => console.log(error))
        },
        [callback]
    )
    return section
}


const AppNav = ({isAuthenticated, onLogout, history}) => {
    const {currentUser: user} = useUser()
    const search = useFormInput('')
    const categories = useSection(getCategories);
    const newspapers = useSection(getNewspapers);

    if (!isAuthenticated) {
        return <></>
    }

    const handleSearchSubmit = event => {
        event.preventDefault()
        history.push(`/search?query=${search.value}`)
    }

    const newspapersSection = makeDropdownMenu(newspapers, 'newspapers')
    const categoriesSection = makeDropdownMenu(categories, 'categories')

    return (
        <>
            <Navbar bg="dark" variant="dark" expand="md">
                <Navbar.Brand as={Link} to={'/'}>
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
                        <FormControl type="text" name='search' placeholder="Buscar" className="mr-sm-2"
                                     {...search} />
                        <Button type={"submit"} variant="outline-success">Buscar</Button>
                    </Form>
                </Navbar.Collapse>
            </Navbar>
        </>
    );

}


export default withRouter(AppNav);

