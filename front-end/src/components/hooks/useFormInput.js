import { useState } from "react"


const useFormInput = initialValue => {
    const [value, setValue] = useState(initialValue)

    const onChange = ({ target }) => {
        const { type, value, checked } = target
        const isCheckbox = type === 'checkbox';
        const newValue = isCheckbox ? checked : value
        setValue(newValue)
    }

    return {
        value,
        onChange,
    }
}

export default useFormInput