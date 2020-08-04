import React, {useState} from 'react'
import {CardElement, useStripe, useElements, Elements} from '@stripe/react-stripe-js';
import {loadStripe} from "@stripe/stripe-js/pure";
import {createSubscription} from "../util/APIUtils";
import {useUser} from "../App";
import {useHistory} from 'react-router-dom'
import {Button, Col, Container, Form, Row} from "react-bootstrap";

import './subscription.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSpinner} from "@fortawesome/free-solid-svg-icons";

const CARD_OPTIONS = {
    iconStyle: 'solid',
    style: {
        base: {
            iconColor: '#c4f0ff',
            // color: '#fff',
            fontWeight: 500,
            fontFamily: 'Roboto, Open Sans, Segoe UI, sans-serif',
            fontSize: '16px',
            fontSmoothing: 'antialiased',
            ':-webkit-autofill': {color: '#fce883'},
            '::placeholder': {color: '#87bbfd'},
        },
        invalid: {
            iconColor: '#ffc7ee',
            color: '#ffc7ee',
        },
    },
};

const Subscription = () => {
    const stripe = useStripe();
    const elements = useElements();

    const {currentUser: user, updateCurrentUser} = useUser()
    const history = useHistory()

    const [processing, setProcessing] = useState(false)

    const handleSubmit = async (event) => {
        // Block native form submission.
        event.preventDefault();

        if (!stripe || !elements) {
            // Stripe.js has not loaded yet. Make sure to disable
            // form submission until Stripe.js has loaded.
            return;
        }

        // Get a reference to a mounted CardElement. Elements knows how
        // to find your CardElement because there can only ever be one of
        // each type of element.
        const cardElement = elements.getElement(CardElement);

        stripe.createToken(cardElement).then(result => {
            if (result.error) {
                // Inform the user if there was an error.
                const errorElement = document.getElementById('card-errors');
                // console.log({errorElement})
                errorElement.textContent = result.error.message;
            } else {
                // Send the tokenId to your server.
                const userId = user.id
                const tokenId = result.token.id;
                const planId = 'price_1HAy5AEoLsnpTFC2pfjKO76T'

                setProcessing(true)

                createSubscription({userId, tokenId, planId})
                    .then(data => {
                        updateCurrentUser({
                            ...user,
                            active: true
                        })
                        history.push('/')
                        // alert(data.details)
                    })
                    .catch(err => alert('No se pudo suscribir'))
                    .finally(() => setProcessing(false))

            }

        })
    }

    const {active} = user

    return (
        <Container>
            <br/>
            <br/>
            <h1 className='display-1'
                style={{
                    marginBottom: '3rem'
                }}
            >
                {
                    active
                        ? 'Ya estas suscrito'
                        : 'Suscribite'
                }
            </h1>

            <div style={{
                fontSize: '2rem',
                marginBottom: '2rem'
            }}>
                <p>Plan mensual de $200</p>
            </div>
            <Row>

                <Form onSubmit={handleSubmit}>
                    <Col>
                        <label className='label-subs'>
                            Agregue su tarjeta
                            <div style={{
                                width: '400px'
                            }}>
                                <CardElement options={CARD_OPTIONS}/>
                            </div>
                            <div id='card-errors'
                                 style={{
                                     color: 'red'
                                 }}
                            />
                        </label>
                    </Col>
                    <Col>

                        <Button className='button-subs' type="submit" disabled={!stripe || active || processing}>
                            {!processing ? 'Suscribir' : <FontAwesomeIcon icon={faSpinner} spin/>}
                        </Button>
                    </Col>
                </Form>
            </Row>
        </Container>
    );
};

const stripePromise = loadStripe('pk_test_51HALsjEoLsnpTFC2ZgjgHXNbGo50Y3399ybtTZNg1mYTlnu6QTUUEkuyoNlE2GTxCJbwOq5N4tvYRmc6IvHUMH5K00z4T8BqLU');

export default () => (
    <Elements stripe={stripePromise}>
        <Subscription/>
    </Elements>
)

