import isLogged from "../helpers/isLogged.js";



const verifyToken = async (req, res, next) => {
    const { decodedToken, currentUser } = await isLogged(req);

    if (!currentUser) {
        res.render("errors/error", {layout: "guest", codError: "403", textError: 'Não Autorizado!'});
    }

    try {
        req.currentUser = currentUser;
        req.decodedToken = decodedToken;
        next();
    } catch (error) {
        console.error('Error verifying token:', error);
        res.render("errors/error", {layout: "guest", codError: "403", textError: 'Não Autorizado!'});
    }
};


export default verifyToken;