import {useEffect, useState} from "react";

export default function useSection(callback) {
    const [section, setSection] = useState([])
    useEffect(
        () => {
            callback()
                .then(response => {
                    setSection(response)
                })
                .catch(error => console.log(error))
        },
        [callback]
    )
    return section
}