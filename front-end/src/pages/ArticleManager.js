import React, {useEffect, useState} from "react";
import LoadingIndicator from "../components/LoadingIndicator";
import {addComment, getArticle, getSimilarArticles} from "../util/APIUtils";
import {Button, Container, Form, FormControl, Row} from "react-bootstrap";
import MoreArticles from "../components/MoreArticles";
import Article from "../components/Article";
import {useParams, useLocation, useHistory} from 'react-router-dom'
import {useUser} from "../App";
import {formatDateTime} from "../util/Helpers";


const ArticleManager = () => {
    const [isLoading, setIsLoading] = useState(true);
    const [article, setArticle] = useState({});
    const [similarArticles, setSimilarArticles] = useState([]);
    const [addCommentInput, setAddCommentInput] = useState('');

    const {id} = useParams();
    const location = useLocation()
    const history = useHistory()
    const {currentUser: {id: userId}} = useUser();

    useEffect(() => {
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


    if (isLoading)
        return <LoadingIndicator/>;

    const compareId = new URLSearchParams(location.search).get('compare')
    const compareArticle = similarArticles.find(({id}) => id === +compareId)
    console.log({compareArticle})
    console.log({compareId})
    console.log(similarArticles)

    const handleCommentSubmit = event => {
        event.preventDefault();
        addComment(userId, id, addCommentInput)
            .then(setArticle)
            .catch(console.log)
        setAddCommentInput('')
    };

    const handleStartCompare = (id) => {
        history.push(location.pathname + `?compare=${id}`)
    };

    const comments = article.comments
        .sort((a, b) => new Date(b.date) - new Date(a.date))
        .map(({id, body, date}) => (
            <p key={id}
               style={{
                   display: 'block',
                   marginRight: '70px',
                   width: '784px',
                   padding: '8px',
                   borderTop: '2px solid gray'
               }}
            >
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
                    {!compareArticle
                        ? (
                            <>
                                <Article {...article} xs={8} share={true}/>
                                <MoreArticles articles={similarArticles} xs={{span: 3, offset: 1}}
                                              onClick={handleStartCompare}
                                />
                            </>
                        )
                        : (
                            <>
                                <Article {...article} share={true}/>
                                <Article {...compareArticle}/>
                            </>
                        )
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
                                         width: '50em',
                                         height: '100px'
                                     }}
                                     required
                                     value={addCommentInput}
                                     onChange={({target}) => setAddCommentInput(target.value)}
                        />
                        <br/>
                        <Button type={"submit"} style={{
                            margin: '50px'
                        }} variant="outline-success">Agregar</Button>
                    </Form>
                </Row>
                <Row>
                    <div>
                        {
                            comments.length
                                ? comments
                                : <p> Todavia no hay comentarios! </p>
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