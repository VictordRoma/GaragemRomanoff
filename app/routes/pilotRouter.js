import express from 'express';

//Controllers
import PilotController from '../controllers/PilotController.js';

//Middlewares
import verifyToken from '../middlewares/verifyToken.js';


class PilotRouter {
    constructor() {
        this.router = express.Router();
        this.PilotController = PilotController;
        this.setupRoutes();
    }

    setupRoutes() {
        //INDEX
        this.router.get('/pilots', verifyToken, (req, res) => this.PilotController.index(req, res));

        //CREATE
        this.router.get('/pilots/create', verifyToken, (req, res) => {
            return res.render('pilot/create', { layout: 'main', title: 'Cadastrar Piloto' });
        });
        this.router.post('/pilots/store', verifyToken, (req, res) => this.PilotController.store(req, res));

        //UPDATE
        this.router.get('/pilots/edit/:id', verifyToken, (req, res) => this.PilotController.edit(req, res));
        this.router.post('/pilots/update', verifyToken, (req, res) => this.PilotController.update(req, res));

        //DELETE
        this.router.post('/pilots/delete', verifyToken, (req, res) => this.PilotController.delete(req, res));
    }

    getRouter(){
        return this.router
    }
}

export default new PilotRouter();