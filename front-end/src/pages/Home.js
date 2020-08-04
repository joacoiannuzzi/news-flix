import React from "react";
import ArticleCardColumns from "../components/ArticleCardColumns";
import {Container} from "react-bootstrap";
import LoadingIndicator from "../components/LoadingIndicator";
import {getLatestArticles} from "../util/APIUtils";
import useGetArticles from "../components/hooks/useGetArticles";

const Home = () => {
    const {isLoading, articles} = useGetArticles(getLatestArticles);

    if (isLoading)
        return <LoadingIndicator/>;

    return (
        <>
            <Container>
                <br/>
                <br/>
                <h1 className='display-1'>Lo ultimo</h1>
                <ArticleCardColumns articles={articles}/>
            </Container>
        </>
    );

};

export default Home