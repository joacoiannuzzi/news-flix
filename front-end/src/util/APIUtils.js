import {API_BASE_URL, ACCESS_TOKEN} from '../constants';

const request = options => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    });

    if (localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const opts = {headers, ...options}

    return fetch(opts.url, opts)
        .then(response =>
            response.json()
                .then(json => {
                return !response.ok ? Promise.reject(json) : json;
            })
        )
};

export function login(loginRequest) {
    return request({
        url: `${API_BASE_URL}/auth/login`,
        method: 'POST',
        body: JSON.stringify(loginRequest)
    })
}

export function changePassword(req) {
    return request({
        url: `${API_BASE_URL}/users/changePassword`,
        method: 'POST',
        body: JSON.stringify(req)
    })
}

export function signup(signupRequest) {
    return request({
        url: `${API_BASE_URL}/auth/signup`,
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}

export function checkEmailAvailability(email) {
    return request({
        url: `${API_BASE_URL}/users/checkEmailAvailability?email=${email}`,
        method: 'GET'
    });
}

export function getCurrentUser() {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: `${API_BASE_URL}/users/me`,
        method: 'GET'
    });
}

export function getUserProfile(id) {
    return request({
        url: `${API_BASE_URL}/users/${id}`,
        method: 'GET'
    });
}

export function getCategories() {
    return request({
        url: `${API_BASE_URL}/articles/categories/all`,
        method: 'GET'
    })
}

export function getCategory(name) {
    return request({
        url: `${API_BASE_URL}/articles/categories/${name}`,
        method: 'GET'
    })
}

export function getNewspapers() {
    return request({
        url: `${API_BASE_URL}/articles/newspapers/all`,
        method: 'GET'
    })
}

export function getNewspaper(name) {
    return request({
        url: `${API_BASE_URL}/articles/newspapers/${name}`,
        method: 'GET'
    })
}

export function getArticle(id) {
    return request({
        url: `${API_BASE_URL}/articles/${id}`,
        method: 'GET'
    })
}

export function getSimilarArticles(id) {
    return request({
        url: `${API_BASE_URL}/articles/similar/${id}`,
        method: 'GET'
    })
}

export function getLatestArticles() {
    return request({
        url: `${API_BASE_URL}/articles/latest`,
        method: 'GET'
    })
}

export function getFilteredArticles(query) {
    return request({
        url: `${API_BASE_URL}/articles/query?query=${query}`,
        method: 'GET'
    })
}

export function getFilteredArticles2({dateFrom, dateTo, category, newspaper, query}) {
    return request({
        url: `${API_BASE_URL}/articles/queryByFilter?dateFrom=${dateFrom}&dateTo=${dateTo}&category=${category}&newspaper=${newspaper}&query=${query}`,
        method: 'GET'
    })
}

export function addOrRemoveFavorite(userId, articleId) {
    return request({
        url: `${API_BASE_URL}/users/addFavorite`,
        method: 'POST',
        body: JSON.stringify({userId, articleId})
    })
}

export function shareArticle(req) {
    return request({
        url: `${API_BASE_URL}/articles/share`,
        method: 'POST',
        body: JSON.stringify(req)
    })
}

export function addComment(userId, articleId, body) {
    console.log({userId, articleId, body});
    return request({
        url: `${API_BASE_URL}/articles/comments/add`,
        method: 'POST',
        body: JSON.stringify({userId, articleId, body})
    })

}




