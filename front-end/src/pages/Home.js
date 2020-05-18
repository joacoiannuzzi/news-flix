import React, {Component} from "react";
import ArticleCardColumns from "../components/ArticleCardColumns";
import {Container} from "react-bootstrap";
import LoadingIndicator from "../components/LoadingIndicator";
import {getLatestArticles} from "../util/APIUtils";

class Home extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            articles: [],
        }
    }

    componentDidMount() {
        getLatestArticles()
            .then(response => {
                this.setState({articles: response, isLoading: false});
            }).catch(error => console.log(error))
    }

    render() {
        const {isLoading, articles} = this.state;

        if (isLoading)
            return (<LoadingIndicator/>);

        return (
            <>
                <Container>
                    <h1>Lo ultimo</h1>
                    <ArticleCardColumns articles={articles}/>
                </Container>
            </>
        );
    }
}

export default Home