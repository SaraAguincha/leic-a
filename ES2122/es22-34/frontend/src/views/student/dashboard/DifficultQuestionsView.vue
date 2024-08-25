<template>
  <v-container fluid>
    <v-card class="table">
      <v-row>
        <v-col>
          <h1>Difficult Questions</h1>
        </v-col>
        <v-col>
          <v-btn color="primary" dark @click="updateDifficultQuestions" data-cy="refreshDifficultQuestions"
            >Refresh
          </v-btn>
        </v-col>
      </v-row>
      <v-data-table
          :headers="headers"
          :items="difficultQuestions"
          :search="search"
          :sort-by="['percentage']"
          sort-desc
          :mobile-breakpoint="0"
          :items-per-page="10"
          :footer-props="{ itemsPerPageOptions: [10, 20, 40, 80] }"
      >
        <template v-slot:[`item.action`]="{ item }">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <v-icon
                  class="mr-2 action-button"
                  v-on="on"
                  @click="showStudentViewDialog(item)"
                  data-cy="showStudentViewDialog"
              >school</v-icon
              >
            </template>
            <span>Student View</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <v-icon
                  class="mr-2 action-button"
                  v-on="on"
                  @click="deleteDifficultQuestion(item)"
                  data-cy="deleteDifficultQuestionButton"
                  color="red"
              >delete</v-icon
              >
            </template>
            <span>Delete Difficult Question</span>
          </v-tooltip>
        </template>
      </v-data-table>
      <student-view-dialog
          v-if="statementQuestion && studentViewDialog"
          v-model="studentViewDialog"
          :statementQuestion="statementQuestion"
          v-on:close-show-question-dialog="onCloseStudentViewDialog"
      />
    </v-card>
  </v-container>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import StudentViewDialog from '@/views/teacher/questions/StudentViewDialog.vue';
import DifficultQuestion from '@/models/dashboard/DifficultQuestion';
import RemoteServices from '@/services/RemoteServices';
import Question from '@/models/management/Question';
import StatementQuestion from '@/models/statement/StatementQuestion';

@Component({
  components: {
    'student-view-dialog': StudentViewDialog,
  },
})
export default class DifficultQuestionsView extends Vue {

  @Prop({ default: 0 })
  readonly dashboardId!: number;

  difficultQuestions: DifficultQuestion[] = [];
  search: string = '';
  statementQuestion: StatementQuestion | null = null;
  studentViewDialog: boolean = false;

  headers: object = [
    {
      text: 'Actions',
      value: 'action',
      align: 'left',
      width: '5px',
      sortable: false,
    },
    {
      text: 'Question',
      value: 'questionDto.title',
      width: '80%',
      align: 'left',
      sortable: false,
    },
    {
      text: 'Percentage',
      value: 'percentage',
      width: '10%',
      align: 'center',
      sortable: true,
    },
  ];

  async created() {
    await this.getDifficultQuestions();
  }

  async showStudentViewDialog(difficultQuestion: DifficultQuestion) {
    if (difficultQuestion.id && difficultQuestion.questionDto.id) {
      try {
        this.statementQuestion = await RemoteServices.getStatementQuestion(
            difficultQuestion.questionDto.id
        );
        this.studentViewDialog = true;
      } catch (error) {
        await this.$store.dispatch('error', error);
      }
    }
  }

  async getDifficultQuestions(){
    await this.$store.dispatch('loading');
    try {
      this.difficultQuestions = await RemoteServices.getDifficultQuestions(this.dashboardId);
    } catch (error) {
      await this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
  }

  async updateDifficultQuestions() {
    await this.$store.dispatch('loading');
    try {
      await RemoteServices.updateDifficultQuestion(this.dashboardId);
    } catch (error) {
      await this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
    await this.getDifficultQuestions();
    let lastCheck = (await RemoteServices.getUserDashboard()).lastCheckDifficultQuestions;
    this.$emit('refresh', lastCheck);
  }

  async deleteDifficultQuestion(toDeleteDifficultQuestion: DifficultQuestion){
    if(toDeleteDifficultQuestion.id && confirm('Do you want to delete this difficult question?')){
      try{
        await RemoteServices.deleteDifficultQuestion(toDeleteDifficultQuestion.id);
        this.difficultQuestions = this.difficultQuestions.filter(difficultQuestion => difficultQuestion.id !== toDeleteDifficultQuestion.id);
      }
      catch (error) {
        await this.$store.dispatch('error', error);
      }
    }
  }

}
</script>

<style lang="scss" scoped>
.question-textarea {
  text-align: left;

  .CodeMirror,
  .CodeMirror-scroll {
    min-height: 200px !important;
  }
}
.option-textarea {
  text-align: left;

  .CodeMirror,
  .CodeMirror-scroll {
    min-height: 100px !important;
  }
}
</style>