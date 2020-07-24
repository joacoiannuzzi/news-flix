import {useEffect, useState} from "react";

const useGetArticles = (callback, dep) => {
    const [articles, setArticles] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
            setIsLoading(true);
            callback(dep)
                .then(articles => {
                    setArticles(articles);
                    setIsLoading(false)
                })
                .catch(console.log)
        },
        [callback, dep]
    );

    return {
        isLoading,
        articles
    }
};

export default useGetArticles