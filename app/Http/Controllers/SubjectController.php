<?php

namespace App\Http\Controllers;
use App\Subjects;
use Illuminate\Http\Request;
use Response;

class SubjectController extends Controller
{
    //Retrieve Subjects for Classes
    public function index($class_id)
    {
        $subjects = Subjects::where('class_id', $class_id)->get()->toArray();
        return Response::json($subjects);
    }
}
