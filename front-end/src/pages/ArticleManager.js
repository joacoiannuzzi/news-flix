import React, {Component} from "react";
import LoadingIndicator from "../components/LoadingIndicator";
import {getArticle, getSimilarArticles} from "../util/APIUtils";
import {Container, Row} from "react-bootstrap";
import MoreArticles from "../components/MoreArticles";
import Article from "../components/Article";


class ArticleManager extends Component {

    constructor(props) {
        super(props);
        this.state = {
            article: {},
            similarArticles: [],
            ArticleCompare: null
        }

    }

    componentDidMount() {
        this.fetchArticles();
    }

    fetchArticles = () => {
        const {id} = this.props.match.params;

        getArticle(id)
            .then(response => {
                this.setState({article: response})
            }).catch(error => console.log(error));

        getSimilarArticles(id)
            .then(response => {
                this.setState({
                    similarArticles: response,
                    isLoading: false
                });
            }).catch(error => console.log(error))
    };

    componentDidUpdate(prevProps) {
        const {id: newId} = this.props.match.params;
        const {id: prevId} = prevProps.match.params;

        if (newId !== prevId) {
            this.setState({
                compareArticle: null
            })
            this.fetchArticles()
        }
    }

    handleCompare = article => {
        this.setState({
            compareArticle: article
        })
    }

    handleStopCompare = () => {
        this.setState({
            compareArticle: null
        })
    }

    render() {

        const {isLoading, article, similarArticles, compareArticle} = this.state;

        if (isLoading)
            return <LoadingIndicator/>;

        return (
            <>
                <Container>
                    <Row>
                        {!compareArticle ?
                            <>
                                <Article {...article} xs={8}/>
                                <MoreArticles articles={similarArticles} xs={{span: 3, offset: 1}}
                                              onClick={this.handleCompare}
                                />
                            </> :
                            <>
                                <Article {...article}/>
                                <Article {...compareArticle} handleStopCompare={this.handleStopCompare}/>
                            </>
                        }
                    </Row>
                </Container>
            </>
        )
    }

}

export default ArticleManager