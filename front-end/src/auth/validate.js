function validate(values) {

    const errors = {}
    const regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
    const regPassword = /(?=.*\d)(?=.*[a-z]).{8,}/

    if(values.email !== undefined && !values.email) {
        errors.email = '이메일을 입력해주세요'
    }else {
        if(regEmail.test(values.email) !== true) {
            errors.email = '이메일 형식으로 입력해주세요'
        }
    }

    if(values.userName !== undefined && !values.userName) {

        errors.userName = '이름을 입력해주세요'
    }else {
    }

    if(values.password !== undefined && !values.password) {
        errors.password = '비밀번호를 입력해주세요'
    }else {
        if(regPassword.test(values.password) !== true){
            errors.password = '영어소문자, 숫자 포함 8자 이상 입력해주세요'
        }
    }


    return errors;

}

export default validate;
