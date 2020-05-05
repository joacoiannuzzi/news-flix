import React, {Component} from "react";
import AppNav from "../components/AppNav";
import ArticleCArdColumns from "../components/ArticleCardColumns";

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
            return (<div>Loading....</div>);

        return (
            <div>
                <AppNav/>
                <ArticleCArdColumns articles={articles}/>
            </div>
        );
    }
}

export default Home