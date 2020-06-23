import React, {useEffect, useState} from "react";
import LoadingIndicator from "../components/LoadingIndicator";
import {addComment, getArticle, getSimilarArticles, getUserProfile} from "../util/APIUtils";
import {Button, Container, Form, FormControl, Row} from "react-bootstrap";
import MoreArticles from "../components/MoreArticles";
import Article from "../components/Article";
import {useParams} from 'react-router-dom'
import useFormInput from "../components/hooks/useFormInput";
import {useUser} from "../App";
import {formatDateTime} from "../util/Helpers";


const ArticleManager = () => {
    const [isLoading, setIsLoading] = useState(true);
    const [article, setArticle] = useState({});
    const [similarArticles, setSimilarArticles] = useState([]);
    const [compareArticle, setCompareArticle] = useState(null);
    const addCommentInput = useFormInput('');

    const {id} = useParams();
    const {currentUser: {id: userId}} = useUser();

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

    const handleCommentSubmit = event => {
        event.preventDefault();
        addComment(userId, id, addCommentInput.value)
            .then(setArticle)
            .catch(console.log)
    };

    const handleStopCompare = () => {
        setCompareArticle(null)
    };

    if (isLoading)
        return <LoadingIndicator/>;


    const comments = article.comments.map(({id, body, date}) => (
        <p key={id} style={{
            display: 'block',
            marginRight: '70px',
            width: '784px',
            padding: '8px',
            borderTop: '2px solid gray'}}>
            {body}
            <span style={{
                marginLeft: '4em',
                width: '85.33%',
                color: 'gray'
            }}>
                {formatDateTime(date)}
            </span>
        </p>
    ));

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

                <br/>
                <br/>
                <h3 className="line"
                     style={{
                         width: '100%',
                         height: '47px',
                         borderBottom: '1px solid #000'
                     }}>

                         Comentarios
                </h3>


                <Row>
                    <Form inline onSubmit={handleCommentSubmit}>
                        <FormControl as={"textarea"}
                                     name='commentBox' placeholder="Agregar comentario"
                                     style={{
                                         width: '30em',
                                         height: '70px'
                                     }}
                                     required
                                     {...addCommentInput}
                        />
                        <p></p>
                        <Button type={"submit"} style={{
                            margin:'50px'}} variant="outline-success">Agregar</Button>
                    </Form>
                </Row>
                <Row>
                    <div>
                        {
                            comments.length
                                ? comments
                                :
                                <p> Todavia no hay comentarios! </p>

                        }
                    </div>
                </Row>
                <br/>
                <br/>
            </Container>
        </>
    )
};


export default ArticleManager