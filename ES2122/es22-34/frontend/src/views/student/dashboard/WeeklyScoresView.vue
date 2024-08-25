<template>
  <v-container fluid>
    <v-card class="table">
      <v-row>
        <v-col>
          <h1>Weekly Scores</h1>
        </v-col>
        <v-col>
          <v-btn
            color="primary"
            dark
            @click="updateWeeklyScores"
            data-cy="refreshWeeklyScores"
            >Refresh
          </v-btn>
        </v-col>
      </v-row>
      <v-data-table
        :headers="headers"
        :items="weeklyScores"
        :search="search"
        :sort-by="['week']"
        sort-desc
        :mobile-breakpoint="0"
        :items-per-page="10"
        :footer-props="{ itemsPerPageOptions: [10, 20, 50, 100] }"
      >
        <template v-slot:[`item.action`]="{ item }">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <v-icon
                class="mr-2 action-button"
                v-on="on"
                @click="deleteWeeklyScore(item)"
                data-cy="deleteWeeklyScoreButton"
                color="red"
                >delete</v-icon
              >
            </template>
            <span>Delete Weekly Score</span>
          </v-tooltip>
        </template>
      </v-data-table>
    </v-card>
  </v-container>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import StudentViewDialog from '@/views/teacher/questions/StudentViewDialog.vue';
import WeeklyScore from '@/models/dashboard/WeeklyScore';
import RemoteServices from '@/services/RemoteServices';
import StatementQuestion from '@/models/statement/StatementQuestion';

@Component({
  components: {
    'student-view-dialog': StudentViewDialog,
  },
})
export default class WeeklyScoresView extends Vue {
  @Prop({ default: 0 })
  readonly dashboardId!: number;

  weeklyScores: WeeklyScore[] = [];
  search: string = '';
  statementQuestion: StatementQuestion | null = null;

  headers: object = [
    {
      text: 'Actions',
      value: 'action',
      align: 'left',
      width: '20%',
      sortable: false,
    },
    {
      text: 'Week',
      value: 'week',
      width: '20%',
      align: 'left',
      sortable: true,
    },
    {
      text: 'Number Answered',
      value: 'numberAnswered',
      width: '20%',
      align: 'center',
      sortable: true,
    },
    {
      text: 'Uniquely Answered',
      value: 'uniquelyAnswered',
      width: '20%',
      align: 'center',
      sortable: true,
    },
    {
      text: 'Percentage Correct',
      value: 'percentageCorrect',
      width: '20%',
      align: 'center',
      sortable: true,
    },
  ];

  async created() {
    await this.getWeeklyScores();
  }

  async getWeeklyScores() {
    await this.$store.dispatch('loading');
    try {
      this.weeklyScores = await RemoteServices.getWeeklyScores(
        this.dashboardId
      );
    } catch (error) {
      await this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
  }

  async updateWeeklyScores() {
    await this.$store.dispatch('loading');
    try {
      await RemoteServices.updateWeeklyScore(this.dashboardId);
    } catch (error) {
      await this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
    await this.getWeeklyScores();
    let lastCheck = (await RemoteServices.getUserDashboard())
      .lastCheckWeeklyScores;
    this.$emit('refresh', lastCheck);
  }

  async deleteWeeklyScore(toDeleteWeeklyScore: WeeklyScore) {
    if (
      toDeleteWeeklyScore.id &&
      confirm('Do you want to delete this weekly score?')
    ) {
      try {
        await RemoteServices.deleteWeeklyScore(toDeleteWeeklyScore.id);
        this.weeklyScores = this.weeklyScores.filter(
          (weeklyScore) => weeklyScore.id !== toDeleteWeeklyScore.id
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
