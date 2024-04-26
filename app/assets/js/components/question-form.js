export default () => {
  return {
    formData: {
      prompt: "",
      answerOptions: [] ,
      description: "" ,
    },
    isSubmitting: false,
    addOption() {
      this.formData.answerOptions.push({ answer: "", isCorrect: true });
    },
    async processFormData() {
      this.isSubmitting = true
      try {
        const res = await fetch("/questions/create", {
          method: "POST",
          body: JSON.stringify(this.formData),
          headers: {
            'Content-Type': 'application/json'
          }
        });
        this.isSubmitting = false;
        
      } catch(err) {
        console.log(err)
      }
    }
  };
};