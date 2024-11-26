import { getAuth, createUserWithEmailAndPassword, signInWithEmailAndPassword, signOut, sendPasswordResetEmail, sendEmailVerification, updatePassword } from "../config/firebase.js";



const auth = getAuth();
const user = auth.currentUser;



class AuthController 
{
  register(req, res) 
  {
    const { email, password } = req.body;

    if (!email || !password) 
    {
      req.session.error = 'Campos Vázios!';

      return res.redirect("/register");
    }

    if(password.length < 6){ 
      req.session.error = 'A Senha deve ter no mínimo 6 caracteres!';

      return res.redirect("/login");
    }

    createUserWithEmailAndPassword(auth, email, password)
      .then((userCredential) => {
        sendEmailVerification(auth.currentUser)
          .then(() => {
            const idToken = userCredential._tokenResponse.idToken;
            req.session.user = { email: userCredential.user.email };

            if (idToken) {
              res.cookie('access_token', idToken, {
                  httpOnly: true
              });

              req.session.success = 'Cadastro Efetuado com Sucesso!';
              res.redirect("/dashboard");
            } 
            else {
              res.render("errors/error", {layout: "guest", codError: "500", textError: 'Erro no Servidor!'});
            }
          })
          .catch((error) => {
            console.error(error);
            res.render("errors/error", {layout: "guest", codError: "500", textError: 'Erro no Servidor!'});
          });
      })
      .catch((error) => {
        console.error(error);

        switch (error.code) {
          case 'auth/email-already-in-use':
            req.session.error = "Já existe um Usuário com este E-mail!";
            break;
          case 'auth/weak-password':
            req.session.error = "A Senha deve ter no mínimo 6 caracteres!";
            break;
          default:
            req.session.error = "Ocorreu um Erro ao Registrar Usuário!";
        }

        res.redirect("/register");
      });
  }



  login(req, res) 
  {
    const { email, password } = req.body;

    if (!email || !password) {
      req.session.error = 'Campos Vázios!';

      return res.redirect("/login");
    }

    signInWithEmailAndPassword(auth, email, password)
      .then((userCredential) => { 
        const idToken = userCredential._tokenResponse.idToken;
        req.session.user = { email: userCredential.user.email };
        
        if (idToken) {
          res.cookie('access_token', idToken, {
              httpOnly: true
          });

          req.session.success = 'Login efetuado com Sucesso!';
          res.redirect("/dashboard");
        } else {
          res.render("errors/error", {layout: "guest", codError: "500", textError: 'Erro no Servidor!'});
        }
      })
      .catch((error) => {
        console.error(error);

        switch (error.code) {
          case 'auth/user-not-found':
            req.session.error = "Usuário não Encontrado!";
            break;
          case 'auth/invalid-credential':
            req.session.error = "Credenciais Incorretas!";
            break;
          default:
            req.session.error = "Ocorreu um Erro ao Logar Usuário!";
        }

        res.redirect("/login");
      });
  }



  logout(req, res) 
  {
    signOut(auth)
      .then(() => {
        res.clearCookie('access_token');
        req.session.success = 'Você saiu do Sistema!';
        res.redirect("/");
      })
      .catch((error) => {
        console.error(error);
        res.render("errors/error", {layout: "guest", codError: "500", textError: 'Erro no Servidor!'});
      });
  }



  resetPassword(req, res)
  {
    const { email } = req.body;

    if (!email ) {
      req.session.error = 'Campos Vázios!';

      return res.redirect("/");
    }

    sendPasswordResetEmail(auth, email)
      .then(() => {
        res.status(200).json({ message: "Password reset email sent successfully!" });
      })
      .catch((error) => {
        console.error(error);

        switch (error.code) {
          case 'auth/user-not-found':
            req.session.error = "Usuário não Encontrado!";
            break;
          default:
            req.session.error = "Ocorreu um Erro ao Resetar Senha!";
        }

        res.redirect("/");
      });
  }



  alterPassword(req, res) 
  {
    const { email, oldPassword, newPassword } = req.body;

    if (!email || !newPassword || !oldPassword) {
      req.session.error = 'Campos Vázios!';

      return res.redirect("/profile");
    }

    if(newPassword.length < 6){
      req.session.error = 'A Senha deve ter no mínimo 6 caracteres!';

      return res.redirect("/profile");
    }

    if(newPassword !== oldPassword){
      req.session.error = 'As Senhas não Conferem!';

      return res.redirect("/profile");
    }

    updatePassword(user, newPassword).then(() => {
      req.session.success = 'Senha Alterada com Sucesso!';
      res.redirect("/profile");
    }).catch(error => {
      console.error(error);
      res.render("errors/error", {layout: "guest", codError: "500", textError: 'Erro no Servidor!'});
    });
  }
}



export default new AuthController();