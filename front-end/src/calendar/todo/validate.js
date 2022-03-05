function validate({ title, todoDate }) {

    const errors = {}
    
    // console.log('validate 시작')

    if(!title) {
        errors.title = '제목을 입력해주세요.'
        // console.log('title:: ', title)
    }

    if(!todoDate) {
        errors.todoDate = '날짜를 입력해주세요.'
        // console.log('todoDate:: ', todoDate)
    }

    // console.log('validate 종료')
    
    return errors;

}

export default validate;