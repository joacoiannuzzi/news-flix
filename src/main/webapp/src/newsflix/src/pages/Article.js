import React, {Component} from "react";
import AppNav from "../components/AppNav";
import {Col, Container, Image, Row} from "react-bootstrap";
import ArticleCards from "../components/ArticleCards";
import * as Arrays from "util";

class Article extends Component {

    constructor(props) {
        super(props);
        this.state = {
            article: {},
            similarArticles: []
        }
    }

    async componentDidMount() {
        const response = await fetch(`/api/articles/${this.props.match.params.id}`, {
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
                        <Col xs={8}>
                            <h1 className="">
                                {article.title}
                            </h1>
                            <h4 className="mt-4">
                                {article.date}
                            </h4>
                            <Image fluid src={article.image}/>
                            <p className="text-justify mt-4">
                                {article.body}

                            </p>
                        </Col>
                        {Arrays.isArray(similarArticles) && similarArticles.length ?
                            <Col xs={{span: 3, offset: 1}}>
                                <h2 className="mb-4">Articulos similares de otros diarios</h2>
                                <ArticleCards className="mb-4" articles={similarArticles}/>
                            </Col> :
                            <></>

                        }
                    </Row>
                </Container>
            </>
        )
    }

}

export default Article