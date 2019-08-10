<?php

namespace App\Http\Controllers;
use App\Modules;
use Illuminate\Http\Request;
use Response;

class ModulesController extends Controller
{
    //Retrieve Modules for Selected Subject
    public function index($subject_id)
    {
        $chapters = Modules::where('subject_id', $subject_id)->get()->toArray();
        return Response::json($chapters);
    }
}
