import React, {useEffect, useState} from "react";
import LoadingIndicator from "../components/LoadingIndicator";
import {getArticle, getSimilarArticles} from "../util/APIUtils";
import {Container, Row} from "react-bootstrap";
import MoreArticles from "../components/MoreArticles";
import Article from "../components/Article";
import {useParams} from 'react-router-dom'


const ArticleManager = props => {
    const [isLoading, setIsLoading] = useState(true);
    const [article, setArticle] = useState({});
    const [similarArticles, setSimilarArticles] = useState([]);
    const [compareArticle, setCompareArticle] = useState(null);
    const {id} = useParams();

    useEffect(
        () => {

            setIsLoading(true);
            getArticle(id)
                .then(response => {
                    setArticle(response)
                })
                .catch(error => console.log(error));

            getSimilarArticles(id)
                .then(response => {
                    setSimilarArticles(response);
                    setIsLoading(false)
                })
                .catch(error => console.log(error))

        },
        [id]
    );

    const handleStopCompare = () => {
        setCompareArticle(null)
    };

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
                                          onClick={setCompareArticle}
                            />
                        </> :
                        <>
                            <Article {...article}/>
                            <Article {...compareArticle} handleStopCompare={handleStopCompare}/>
                        </>
                    }
                </Row>
            </Container>
        </>
    )
};


export default ArticleManager