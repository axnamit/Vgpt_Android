<?php

namespace App\Http\Controllers;
use App\Classes;
use Illuminate\Http\Request;
use Response;

class StandardController extends Controller
{
    //Retrieve Classes
    public function index()
    {
        $classes = Classes::all()->toArray();
        return Response::json($classes);
    }
}
