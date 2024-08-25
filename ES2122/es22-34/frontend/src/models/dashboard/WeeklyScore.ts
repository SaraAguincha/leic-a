import { ISOtoString } from '@/services/ConvertDateService';

export default class WeeklyScore {
  id!: number;
  week!: string;
  numberAnswered!: number;
  uniquelyAnswered!: number;
  percentageCorrect!: number;

  constructor(jsonObj?: WeeklyScore) {
    if (jsonObj) {
      this.id = jsonObj.id;
      if (jsonObj.week) this.week = ISOtoString(jsonObj.week);
      if (jsonObj.numberAnswered || jsonObj.numberAnswered == 0)
        this.numberAnswered = jsonObj.numberAnswered;
      if (jsonObj.uniquelyAnswered || jsonObj.uniquelyAnswered == 0)
        this.uniquelyAnswered = jsonObj.uniquelyAnswered;
      if (jsonObj.percentageCorrect || jsonObj.percentageCorrect == 0)
        this.percentageCorrect = jsonObj.percentageCorrect;
    }
  }
}
