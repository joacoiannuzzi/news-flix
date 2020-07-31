import React from 'react'
import {CardElement, useStripe, useElements, Elements} from '@stripe/react-stripe-js';
import {loadStripe} from "@stripe/stripe-js/pure";
import {createSubscription} from "../util/APIUtils";
import {useUser} from "../App";

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

    const {currentUser: {id: userId}} = useUser()

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

        // Use your card Element with other Stripe.js APIs
        // const {error, paymentMethod} = await stripe.createPaymentMethod({
        //     type: 'card',
        //     card: cardElement,
        // });

        stripe.createToken(cardElement).then(result => {
            if (result.error) {
                // Inform the user if there was an error.
                const errorElement = document.getElementById('card-errors');
                errorElement.textContent = result.error.message;
            } else {
                // Send the tokenId to your server.
                const tokenId = result.token.id;
                const planId = 'price_1HAy5AEoLsnpTFC2pfjKO76T'
                createSubscription({userId, tokenId, planId})
                    .then(data => alert(data.details))

            }

            // if (error) {
            //     console.log('[error]', error);
            // } else {
            //     console.log('[PaymentMethod]', paymentMethod);
            // }
        })
    }

    return (
        <form onSubmit={handleSubmit}>
            <div style={{
                width: '400px'
            }}>
                <CardElement options={CARD_OPTIONS}/>
            </div>
            <button type="submit" disabled={!stripe}>
                Pay
            </button>
        </form>
    );
};

const stripePromise = loadStripe('pk_test_51HALsjEoLsnpTFC2ZgjgHXNbGo50Y3399ybtTZNg1mYTlnu6QTUUEkuyoNlE2GTxCJbwOq5N4tvYRmc6IvHUMH5K00z4T8BqLU');

export default () => (
    <Elements stripe={stripePromise}>
        <Subscription/>
    </Elements>
)

