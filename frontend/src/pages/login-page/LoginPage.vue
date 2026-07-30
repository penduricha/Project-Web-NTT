<script>
import './login-page.scss';
import './form-event.scss';
import '@/assets/main-scss/theme-color.scss';
import RouterManagement from "@/routers/RouterManagement.js";
import ButtonRed from "@/components/button/button-red/ButtonRed.vue";
import TextInvalid from "@/components/span/TextInvalid.vue";
import PasswordManagement from "@/manage-data/PasswordManagement.js";
import StudentLocalStorage from "@/local-storage/StudentLocalStorage.js";
import { StudentAccount } from "@/models/StudentAccount.js";
import StudentDao from "@/daos/StudentDao.js";
import Footer from "@/components/footer/Footer.vue";
import Header from "@/components/header/Header.vue";
// import '../../components/button/button-blue/button-blue.scss';

export default {
  name: "LoginPage",

  components: {
    TextInvalid,
    ButtonRed,
    Header,
    Footer,
  },

  beforeCreate() {

  },

  created() {
    this.saveRouterPath(this.getRoute());
    this.getStatusCheckBoxRememberMe();
  },

  mounted() {
    this.setTitlePage();
  },

  data() {
    return {
      studentID: null,
      password: null,
      validateLogin: "",
      rememberMe: false,

      //btn login
      loadingButtonLogin: false,
      textButtonLogin: 'Đăng nhập',
      disableButtonLogin: false,

      //event form
      disableFormLogin: false,
    }
  },

  methods: {

    setTitlePage() {
      document.title = 'Đăng nhập';
    },

    getRoute() {
      //ở đây có props thì phải thêm path của props
      return this.$route.path;
    },

    saveRouterPath(route) {
      const routerManagement = new RouterManagement();
      routerManagement.savePath_To_SessionStorage(route);
    },

    //lock paste
    preventPaste(event) {
      event.preventDefault();
    },

    isNumber(evt) {
      const charCode = (evt.which) ? evt.which : evt.keyCode;
      // Các mã ASCII từ 48 đến 57 tương ứng với các số từ 0 đến 9
      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        evt.preventDefault(); // Chặn không cho nhập ký tự đó vào input
      }
    },

    setInputStudentID(event) {
      // Chỉ cho phép các ký tự số
      const numericValue = event.target.value.replace(/[^0-9]/g, '');
  
      // Gán lại giá trị cho biến studentID
      this.studentID = numericValue;

      if (!this.studentID) {
        this.validateLogin = null;
      } else {
        this.validateLogin = null;
      }
    },

    setInputPassword() {
      if (this.password) {
        this.validateLogin = null;
      } else {
        this.validateLogin = null;
      }
    },

    async fetchAccountStudent(studentID, password) {
      return await StudentDao
        .getStudentIDAndPassword(studentID, password);

    },

    navigateToRegisterCoursesPage() {
      //save path to local storage
      const routerManagement = new RouterManagement();
      const studentLocalStorage = new StudentLocalStorage();

      const routerPathToSave = '/information-student';
      routerManagement.savePath_To_LocalStorage(routerPathToSave);

      //save student ID
      studentLocalStorage.setStudentIDToLocalStorage(this.studentID);

      this.$router.replace({
        path: routerPathToSave,
        // query: {
        // }
      }).then(() => {
        // Delay the reload to ensure the navigation is completed
        // Adjust the timeout as needed
        setTimeout(() => {
           window.location.reload();
        }, 50);
      }).catch((error) => {
        console.error('Error navigating :', error);
        alert(error);
      });
    },

    loadButtonLogin() {
      this.loadingButtonLogin = true;
      this.disableButtonLogin = true;
      this.textButtonLogin = null;
      this.disableFormLogin = true;
    },

    stopLoadButtonLogin() {
      this.loadingButtonLogin = false;
      this.disableButtonLogin = false;
      this.textButtonLogin = 'Đăng nhập';
      this.disableFormLogin = false;
    },

    async handleLoginEvent() {
      const nullFieldInput = !this.studentID || !this.password;
      if (nullFieldInput) {
        this.validateLogin = 'Vui lòng nhập đầy đủ thông tin.';
      } else {
        this.loadButtonLogin();

        const passwordManagement = new PasswordManagement(this.password.trim());
        let passwordHashed = await passwordManagement.sha512Hash();
        console.log('Password hashed:', passwordHashed);

        let studentFetched = await this.fetchAccountStudent(this.studentID.trim());
        console.log('Student fetched:', studentFetched);

        if (!studentFetched) {
          this.validateLogin = 'Mã số sinh viên hoặc mật khẩu không đúng.';
          this.stopLoadButtonLogin();
        } else {
          if (studentFetched.password !== passwordHashed) {
            this.validateLogin = 'Mã số sinh viên hoặc mật khẩu không đúng.';
          } else {
            //save remember me
            this.validateLogin = null;
            if (this.rememberMe === true) {
              this.saveDataInputToLocalStorage();
            } else {
              this.removeDataInputFromLocalStorage();
            }
            //save student id to local storage
            console.log('Login successfully');
            this.navigateToRegisterCoursesPage();
          }
          this.stopLoadButtonLogin();
        }
      }
      //Mã số sinh viên hoặc mật khẩu không đúng.
      //this.loadButtonLogin();
    },

    removeDataInputFromLocalStorage() {
      const studentLocalStorage = new StudentLocalStorage();
      studentLocalStorage.removeLocalStorageRememberMe();
    },

    saveDataInputToLocalStorage() {
      if (this.studentID && this.password) {
        const studentLocalStorage = new StudentLocalStorage();
        studentLocalStorage.saveLocalStorageRememberMe(
          this.studentID.trim(),
          this.password.trim());
      }
    },

    getStatusCheckBoxRememberMe() {
      /* neu local storage có save thì true ngược lại thì false */
      /* neu tru thi set input*/
      const studentLocalStorage = new StudentLocalStorage();
      let studentFetched = studentLocalStorage
        .getLocalStorageRememberMe();
      console.log('Student fetched from local storage remember me:', studentFetched);
      if (!studentFetched || (
        typeof studentFetched === 'object'
        && Object.keys(studentFetched).length === 0)) {
        this.rememberMe = false;
      } else {
        this.rememberMe = true;
        this.setDataInputFromLocalStorage(studentFetched);
      }
    },

    setDataInputFromLocalStorage(studentFetched) {
      if (studentFetched) {
        let studentID = studentFetched.studentID;
        let password = studentFetched.password;
        const studentAccount = new StudentAccount(studentID, password);
        console.log(studentAccount.toString());
        this.studentID = studentAccount.getStudentID();
        this.password = studentAccount.getPassword();
      }
    }
  },

  computed: {
    // setBorderColorChoose() {
    //   return (index) => {
    //     return (index === this.indexQuestion)
    //         ? 'border-choose'
    //         : 'border-no-choose';
    //   };
    // },
    setDisableForm() {
      return (this.disableFormLogin) ? 'disabled' : 'allow';
    }
  },
}
</script>

<template>
  <div class="container-page-login">
    <Header />
    <section class="container-form-login-page">
      <div class="form-login" :class="['event-form-login', setDisableForm]">
        <h4 class="title-login">ĐĂNG NHẬP</h4>
        <div class="form-floating mb-3 style-input-login">
          <input type="text" class="form-control" 
            id="floatingInputStudentID" 
            placeholder="Mã sinh viên (nhập số)"
            @input="setInputStudentID" 
            @keypress="isNumber($event)"
            v-model="studentID" maxlength="10">
          <label for="floatingInputStudentID">Mã sinh viên (nhập số)</label>
        </div>
        <div class="form-floating mb-3 style-input-login">
          <input type="password" class="form-control" id="floatingInputPassword" placeholder="Mật khẩu"
            @input="setInputPassword" v-model="password" @paste="preventPaste($event)" maxlength="20">
          <label for="floatingInputPassword">Mật khẩu</label>
        </div>

        <TextInvalid :text-span="validateLogin"/>

        <div class="box-remember-me">
          <input type="checkbox" id="rememberMe" v-model="rememberMe" @change="saveDataInputToLocalStorage()"
            class="style-checkbox" />
          <span>Ghi nhớ đăng nhập</span>
        </div>

        <ButtonRed class="btn-login" 
          @click="handleLoginEvent()" 
          :text-button="textButtonLogin"
          :loading-button="loadingButtonLogin" 
          :disable-button="disableButtonLogin" 
          />
      </div>
    </section>
    <Footer/>
  </div>

</template>

<style scoped lang="scss">
// .container-page-login {
//   display: flex;
//   flex-direction: column;
//   justify-content: space-between;
// }
</style>