import {Col, Image} from "react-bootstrap";
import React from "react";


const Article = (props) => {

    const {title, date, body, image, xs} = props

    return (
        <Col xs={xs}>
            <h1 className="">
                {title}
            </h1>
            <h4 className="mt-4">
                {date}
            </h4>
            <Image fluid src={image}/>
            <p className="text-justify mt-4">
                {body}

            </p>
        </Col>

    )


}

export default Article