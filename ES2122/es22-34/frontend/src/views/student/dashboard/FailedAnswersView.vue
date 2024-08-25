<template>
  <v-container fluid>
    <v-card class="table">
      <v-data-table
        :headers="headers"
        :custom-filter="customFilter"
        :items="failedAnswers"
        :search="search"
        :sort-by="['collected']"
        sort-desc
        :mobile-breakpoint="0"
        :items-per-page="10"
        :footer-props="{ itemsPerPageOptions: [10, 20, 50, 100] }"
      >
        <template v-slot:top>
          <v-card-title>
            <v-text-field
              v-model="search"
              append-icon="search"
              label="Search"
              class="mx-2"
            />
            <v-spacer />
            <v-btn color="primary" dark @click="refreshFailedAnswers" data-cy="refreshFailedAnswers"
              >Refresh</v-btn
            >
          </v-card-title>
        </template>

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
                data-cy="deleteFailedAnswerButton"
                @click="deleteFailedAnswer(item)"
                color="red"
                >delete</v-icon
              >
            </template>
            <span>Delete Failed Answer</span>
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
import { Component, Vue, Prop } from 'vue-property-decorator';
import RemoteServices from '@/services/RemoteServices';
import Question from '@/models/management/Question';
import StatementQuestion from '@/models/statement/StatementQuestion';
import StudentViewDialog from '@/views/teacher/questions/StudentViewDialog.vue';
import FailedAnswer from '@/models/dashboard/FailedAnswer';

@Component({
  components: {
    'student-view-dialog': StudentViewDialog,
  },
})
export default class FailedAnswersView extends Vue {
  @Prop({ required: true })
  readonly dashboardId!: number;

  @Prop({ required: true })
  lastCheckFailedAnswers!: string;

  failedAnswers: FailedAnswer[] = [];
  statementQuestion: StatementQuestion | null = null;
  studentViewDialog: boolean = false;
  search: string = '';

  headers: object = [
    {
      text: 'Actions',
      value: 'action',
      align: 'left',
      width: '10%',
      sortable: false,
    },
    { text: 'Question',
      value: 'questionAnswerDto.question.title',
      width: '60%',
      align: 'left' 
    },
    {
      text: 'Answered',
      value: 'answered',
      width: '15%',
      align: 'center' 
    },
    {
      text: 'Collected',
      value: 'collected',
      width: '15%',
      align: 'center',
    },
  ];

  async created() {
    await this.$store.dispatch('loading');
    try {
      this.failedAnswers = await RemoteServices.getFailedAnswers(this.dashboardId);
    } catch (error) {
      await this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
  }

  customFilter(search: string, question: Question) {
    // noinspection SuspiciousTypeOfGuard,SuspiciousTypeOfGuard
    return (
      search != null &&
      JSON.stringify(question).toLowerCase().indexOf(search.toLowerCase()) !==
        -1
    );
  }

  async showStudentViewDialog(failedAnswer: FailedAnswer) {
    if (failedAnswer.id && failedAnswer.questionAnswerDto.question.id) {
      try {
        this.statementQuestion = await RemoteServices.getStatementQuestion(
          failedAnswer.questionAnswerDto.question.id
        );
        this.studentViewDialog = true;
      } catch (error) {
        await this.$store.dispatch('error', error);
      }
    }
  }

  onCloseStudentViewDialog() {
    this.statementQuestion = null;
    this.studentViewDialog = false;
  }

  async refreshFailedAnswers() {
    await this.$store.dispatch('loading');
    try {
      let failedAnswers: FailedAnswer[] = await RemoteServices.updateFailedAnswers(this.dashboardId);
      if (failedAnswers != null){
        this.failedAnswers = failedAnswers;
        this.$emit('refresh');
      }
    } catch (error) {
      await this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
  }

  async deleteFailedAnswer(toDeleteFailedAnswer: FailedAnswer) {
    if (
      toDeleteFailedAnswer.id &&
      confirm('Are you sure you want to delete this failed answer?')
    ) {
      try {
        await RemoteServices.deleteFailedAnswer(toDeleteFailedAnswer.id);
        this.failedAnswers = this.failedAnswers.filter(
          (failedAnswer) => failedAnswer.id != toDeleteFailedAnswer.id
        );
      } catch (error) {
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
