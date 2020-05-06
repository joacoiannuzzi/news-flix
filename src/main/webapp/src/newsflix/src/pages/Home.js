import React, {Component} from "react";
import AppNav from "../components/AppNav";
import ArticleCardColumns from "../components/ArticleCardColumns";
import {Container} from "react-bootstrap";

class Home extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            articles: [],
        }
    }

    async componentDidMount() {
        const response = await fetch('/api/articles/latest', {
            method: 'GET',
            headers: {
                'content-type': 'application/json',
                'Accept': 'application/json'
            }
        });
        const body = await response.json();
        this.setState({articles: body, isLoading: false});
    }

    render() {
        const {isLoading, articles} = this.state;

        if (isLoading)
            return (<div style={{width: "50%", margin: "0px auto"}}>Loading...</div>);

        return (
            <div>
                <AppNav/>
                <Container>
                    <h1>Lo ultimo</h1>
                    <ArticleCardColumns articles={articles}/>
                </Container>
            </div>
        );
    }
}

export default Home