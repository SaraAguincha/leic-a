import { ISOtoString } from '@/services/ConvertDateService';
import Question from '../management/Question';

export default class DifficultQuestion {
    id!: number;
    percentage!: number;
    questionDto!: Question;

    constructor(jsonObj?: DifficultQuestion) {
        if (jsonObj) {
            this.id = jsonObj.id;
            this.percentage = jsonObj.percentage;
            if (jsonObj.questionDto) {
                this.questionDto = jsonObj.questionDto;
            }
        }
    }
}