<#import "../layout/main.ftl" as layout />
<@layout.header>
  <h1>New question</h1>
  <div x-data="questionForm">
  <form id="question-form" method="post" @submit.prevent="processFormData">
    <div class="field">
      <label for="prompt" class="label">Prompt</label>
      <input
        name="prompt"
        id="prompt"
        class="textarea"
        :disabled="isSubmitting"
        x-model="formData.prompt"
        ></textarea>
    </div>

    <div class="columns is-flex-direction-row is-multiline">
    <template
      x-for="(answerOption, index) in formData.answerOptions"
      :key="index"
    >
        <div class="column is-6">
        <h3 class="title is-3" x-text="'Answer ' + (index+1)"></h3>

        <p x-text="answerOption.answer"></p>
        <div class="field">
          <label :for="'answerOptions['+ index +'][answer]'" class="label"
            >Answer</label
          >
          <div class="control">
            <textarea
              :value="index"
              :name="'answerOptions['+ index +'][answer]'"
              class="textarea"
              :id="'answerOptions['+ index +'][answer]'"
              :disabled="isSubmitting"
              x-model="answerOption.answer"></textarea>
          </div>
        </div>

        <div class="field">
          <div class="control">
            <label
              class="checkbox"
              :for="'answerOptions['+ index +'][isCorrect]'"
            >
              <input
                type="checkbox"
                :name="'answerOptions['+ index +'][isCorrect]'"
                :id="'answerOptions['+ index +'][isCorrect]'"
                :disabled="isSubmitting"
                x-model="answerOption.isCorrect"
              />
              Correct
            </label>
          </div>
        </div>
      </div>
    </template>
  </div>
    <div class="field">
      <button
        @click="addOption"
        class="js-add-answer-option button is-info"
        type="button"
        :disabled="isSubmitting"
      >
        Add answer option</button
      >
    </div>

    <div class="field">
      <button
      type="submit"
      form="question-form"
      class="button is-primary"
      :disabled="isSubmitting"
        >Save</button
      >
    </div>
  </form>
</div>
</@layout.header>