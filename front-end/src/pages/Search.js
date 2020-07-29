import React, {useEffect, useState} from "react";
import ArticleCardColumns from "../components/ArticleCardColumns";
import {Button, Container, Form} from "react-bootstrap";
import LoadingIndicator from "../components/LoadingIndicator";
import {getCategories, getFilteredArticles2, getNewspapers} from "../util/APIUtils";
import useFormInput from "../components/hooks/useFormInput";
import useSection from "../components/hooks/useSection";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";

const makeDropdownMenu = (list) => (
    list.map(item => (
            <option key={item}>
                {item}
            </option>
        )
    )
)

const Search = () => {
    const categories = useSection(getCategories);
    const newspapers = useSection(getNewspapers);

    // const urlSearchParams = new URLSearchParams(search);

    const searchInput = useFormInput(/*urlSearchParams.get('query') ?? */'')
    const categoryInput = useFormInput(/*urlSearchParams.get('category') ?? */'--')
    const newspaperInput = useFormInput(/*urlSearchParams.get('newspaper') ?? */'--')
    const [dateFromInput, setDateFromInput] = useState(/*urlSearchParams.get('dateFrom') ??*/ new Date())
    const [dateToInput, setDateToInput] = useState(/*urlSearchParams.get('dateTo') ??*/ new Date())


    const [updater, update] = useState(0)

    const [articles, setArticles] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
            if (updater === 0) return
            setIsLoading(true);

            // console.log(dateFromInput.toLocaleDateString().replace(/\//g, '-'))
            const request = {
                dateFrom: dateFromInput,
                dateTo: dateToInput,
                category: categoryInput.value,
                newspaper: newspaperInput.value,
                query: searchInput.value
            }

            getFilteredArticles2(request)
                .then(articles => {
                    console.log({articles})
                    setArticles(articles);
                })
                .catch(err => {
                    console.log({err})
                })
                .finally(() => setIsLoading(false))

        },
        // eslint-disable-next-line
        [updater]
    )

    if (isLoading)
        return <LoadingIndicator/>;

    const handleSearchSubmit = event => {
        event.preventDefault()
        update(c => c + 1)
    }

    const newspapersSection = makeDropdownMenu(newspapers)
    const categoriesSection = makeDropdownMenu(categories)

    return (
        <>
            <Container>
                <Form onSubmit={handleSearchSubmit}>
                    <Form.Group controlId="Categorias">
                        <Form.Label>Categorias</Form.Label>
                        <Form.Control as="select" {...categoryInput}>
                            <option>--</option>
                            {categoriesSection}
                        </Form.Control>
                    </Form.Group>

                    <Form.Group controlId="Diarios">
                        <Form.Label>Diarios</Form.Label>
                        <Form.Control as="select" {...newspaperInput}>
                            <option>--</option>
                            {newspapersSection}
                        </Form.Control>
                    </Form.Group>

                    <Form.Group controlId="desde">
                        <Form.Label>Desde</Form.Label>
                        <DatePicker
                            selected={dateFromInput}
                            onChange={setDateFromInput}
                        />
                    </Form.Group>

                    <Form.Group controlId="hasta">
                        <Form.Label>Hasta</Form.Label>
                        <DatePicker
                            selected={dateToInput}
                            onChange={setDateToInput}
                        />
                    </Form.Group>

                    <Form.Group controlId="formBasicEmail">
                        <Form.Control type="text" placeholder="Buscar" {...searchInput}/>
                    </Form.Group>

                    <Button variant="primary" type="submit">
                        Submit
                    </Button>

                </Form>
                {articles.length === 0
                    ? <h2>No hay resultados</h2>
                    : <ArticleCardColumns articles={articles}/>
                }
            </Container>
        </>
    );

};

export default Search