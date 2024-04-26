import "../scss/styles.scss"
import Alpine from 'alpinejs'
import questionForm from "./components/question-form.js"

window.Alpine = Alpine
Alpine.data("questionForm", questionForm)

Alpine.start()

