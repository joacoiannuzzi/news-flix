import React, {Component} from "react";
import {Col, Container, Image, Row} from "react-bootstrap";
import ArticleCards from "../components/ArticleCards";
import * as Arrays from "util";
import LoadingIndicator from "../components/LoadingIndicator";
import {getArticle, getSimilarArticles} from "../util/APIUtils";

class Article extends Component {

    constructor(props) {
        super(props);
        this.state = {
            article: {},
            similarArticles: []
        }
    }

    componentDidMount() {
        const {id} = this.props.match.params;

        getArticle(id)
            .then(response => {
                this.setState({article: response});
            }).catch(error => console.log(error))

        getSimilarArticles(id)
            .then(response => {
                this.setState({
                    similarArticles: response,
                    isLoading: false
                });
            }).catch(error => console.log(error))
    }

    render() {

        const {isLoading, article, similarArticles} = this.state;

        if (isLoading)
            return <LoadingIndicator/>;

        return (
            <>
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