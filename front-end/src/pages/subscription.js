import {Helmet} from "react-helmet";
import React from "react";


const Subscription = () => {

    return (
        <>
                <Helmet>
                    <script src="https://js.stripe.com/v3" type="text/javascript" />
                    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" type="text/javascript" />
                    <script src="https://code.jquery.com/jquery-3.3.1.min.js" type="text/javascript" />
                    <script src="https://pastebin.com/raw/tr4x0XhU" type="text/javascript" />
                </Helmet>
                <section class="py-5">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-6 col-md-8 col-12 my-auto mx-auto">
                                <h1>
                                    Stripe Recurring Subscription
                                </h1>
                                <p class="lead mb-4">
                                    Please fill the form below to complete the payment
                                </p>
                                <h5 class="mb-2">Choose your payment plan</h5>
                                <p class="text-muted">
                                    16% OFF when you upgrade to annual plan.
                                </p>
                                <div class="py-2">
                                    <div class="custom-control custom-radio">
                                        <input class="custom-control-input" id="monthly-plan" name="premium-plan"
                                               type="radio"
                                               value="price_1HAy5AEoLsnpTFC2pfjKO76T"/>
                                        <label class="custom-control-label" for="monthly-plan">
                                            <strong>Monthly $5.00</strong><br/>
                                            <small class="text-muted">
                                                Pay $5 every month and get access to all premium features.
                                            </small>
                                        </label>
                                    </div>
                                    <div class="custom-control custom-radio mt-3">
                                        <input checked="" class="custom-control-input" id="annually-plan"
                                               name="premium-plan"
                                               type="radio" value="price_1HAys2EoLsnpTFC2HAYWvYQW"/>
                                        <label class="custom-control-label" for="annually-plan">
                                            <strong>Yearly $50.00</strong>
                                            <span class="badge badge-primary ml-1">16% OFF</span>
                                            <br/>
                                            <small class="text-muted">
                                                Pay $50 every year and get access to all premium features.
                                            </small>
                                        </label>
                                    </div>
                                </div>
                                <form action="#" id="payment-form" method="post">
                                    <input id="api-key" type="hidden" value="pk_test_51HALsjEoLsnpTFC2ZgjgHXNbGo50Y3399ybtTZNg1mYTlnu6QTUUEkuyoNlE2GTxCJbwOq5N4tvYRmc6IvHUMH5K00z4T8BqLU"/>
                                    <div class="form-group">
                                        <label class="font-weight-medium" for="card-element">
                                            Enter credit or debit card below
                                        </label>
                                        <div class="w-100" id="card-element">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" id="email" name="email"
                                               placeholder="Email Address" type="email" required/>
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" id="coupon" name="coupon"
                                               placeholder="Coupon code (optional)" type="text"/>
                                    </div>
                                    <div class="text-danger w-100" id="card-errors" role="alert"/>
                                    <div class="form-group pt-2">
                                        <button class="btn btn-primary btn-block" id="submitButton" type="submit">
                                            Pay With Your Card
                                        </button>
                                        <div class="small text-muted mt-2">
                                            Pay securely with Stripe. By clicking the button above, you agree
                                            to our <a target="_blank" href="#">Terms of Service</a>,
                                            <a target="_blank" href="#"> Privacy</a> and
                                            <a target="_blank" href="#"> Refund</a> policies.

                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </section>





        </>
    );

};

export default Subscription
