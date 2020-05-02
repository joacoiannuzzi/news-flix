import React, {Component} from "react";
import AppNav from "../components/AppNav";
import {Card, CardColumns} from "react-bootstrap";
import {Link} from "react-router-dom";

class Home extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            articles: [],
        }
    }

    async componentDidMount() {

        const response = await fetch('/api/articles', {
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


        let cards = articles.map(article => {
                if (article.image != null && article.image !== "" && article.image !== undefined)
                    return (
                        <Card>
                            <Link to={"/articles/" + article.id}>
                                <Card.Img top width="100%" src={article.image}/>
                                <Card.Body>
                                    <Card.Title>
                                        {article.title}
                                    </Card.Title>
                                </Card.Body>
                            </Link>
                        </Card>
                    );
                return (
                    <Card>
                        <Link to={"/articles/" + article.id}>
                            <Card.Body>
                                <Card.Title>
                                    {article.title}
                                </Card.Title>
                            </Card.Body>
                        </Link>
                    </Card>
                )
            }
        );


        return (
            <div>
                <AppNav/>
                <CardColumns className="mx-3 mt-2">
                    {cards}
                </CardColumns>
            </div>
        );
    }
}

export default Home