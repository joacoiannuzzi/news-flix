import React, {Component} from "react";
import ArticleCardColumns from "../components/ArticleCardColumns";
import {Container} from "react-bootstrap";
import LoadingIndicator from "../components/LoadingIndicator";
import {getFilteredArticles} from "../util/APIUtils";

class Search extends Component {

    constructor(props) {
        super(props);
        this.state = {
            search: this.getQueryParams(props),
            isLoading: true,
            articles: [],
        }
    }

    componentDidMount() {
        this.fetchArticles();
    }

    fetchArticles = () => {
        const query = this.getQueryParams(this.props);
        getFilteredArticles(query)
            .then(response => {
                this.setState({
                    search: query,
                    articles: response,
                    isLoading: false
                });
            }).catch(error => console.log(error))
    };

    getQueryParams = props => new URLSearchParams(props.location.search).get('query');

    componentDidUpdate(prevProps) {
        const currentQuery = this.getQueryParams(this.props);
        const prevQuery = this.getQueryParams(prevProps);
        if (currentQuery !== prevQuery) {
            this.setState({
                isLoading: true
            });
            this.fetchArticles()
        }
    }

    render() {
        const {isLoading, articles, search} = this.state;

        if (isLoading)
            return <LoadingIndicator/>;

        return (
            <>
                <Container>
                    <h1>Resultado para: {search}</h1>
                    <ArticleCardColumns articles={articles}/>
                </Container>
            </>
        );
    }
}

export default Search