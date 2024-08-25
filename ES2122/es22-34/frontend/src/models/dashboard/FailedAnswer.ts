import { ISOtoString } from '@/services/ConvertDateService';
import { QuestionAnswer } from '../management/QuestionAnswer';

export default class FailedAnswer {
    id!: number;
    collected!: string;
    answered!: string;
    questionAnswerDto!: QuestionAnswer;

    constructor(jsonObj?: FailedAnswer) {
        if (jsonObj) {
            this.id = jsonObj.id;
            if (jsonObj.collected)
                this.collected = ISOtoString(jsonObj.collected);
            if (jsonObj.answered)
                this.answered = jsonObj.answered? "Yes" : "No";
            if (jsonObj.questionAnswerDto)
                this.questionAnswerDto = jsonObj.questionAnswerDto;
        }
    }
}
