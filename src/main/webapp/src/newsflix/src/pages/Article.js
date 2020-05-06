import React, {Component, useEffect, useState} from "react";
import AppNav from "../components/AppNav";
import {Card, CardDeck, Col, Container, Row} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import ArticleCardColumns from "../components/ArticleCardColumns";
import ArticleCards from "../components/ArticleCards";

class Article extends Component {

    constructor(props) {
        super(props);
        this.state = {
            article: {},
            similarArticles: []
        }
    }

    async componentDidMount() {
        const response = await fetch(`/api/article/${this.props.match.params.id}`, {
            method: 'GET',
            headers: {
                'content-type': 'application/json',
                'Accept': 'application/json'
            }
        });
        const body = await response.json();
        this.setState({article: body});

        const response2 = await fetch(`/api/articles/similar/${this.props.match.params.id}`, {
            method: 'GET',
            headers: {
                'content-type': 'application/json',
                'Accept': 'application/json'
            }
        });
        const body2 = await response2.json();
        this.setState({similarArticles: body2, isLoading: false});


    }

    render() {

        const {isLoading, article, similarArticles} = this.state;

        if (isLoading)
            return (<div style={{width: "50%", margin: "0px auto"}}>Loading...</div>);

        return (
            <>
                <AppNav/>
                <Container className="mt-4">
                    <Row>
                        <Col xs={9}>
                            <h1 className="">
                                {article.title}
                            </h1>
                            <h4 className="mt-4">
                                {article.date}
                            </h4>
                            <p className="text-justify mt-4">
                                {article.body}

                            </p>
                        </Col>
                        <Col xs={3}>
                            <h2 className="mb-4">Articulos similares de otros diarios</h2>
                            <ArticleCards articles={similarArticles}/>
                        </Col>
                    </Row>
                </Container>
            </>
        )
    }

}

export default Article