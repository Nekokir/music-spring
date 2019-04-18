var ImgBlur = (function(GLI){
    var canvas = document.createElement('canvas'),
        gl = GLI.createGL(canvas);
    var canvas_for_change_size = document.createElement('canvas'),
        ctx = canvas_for_change_size.getContext('2d');
    if(gl === null){
        return {
            glReady : false
        };
    }

    var program = GLI.createProgram(gl, document.getElementById('VS').innerHTML, document.getElementById('FS').innerHTML),
        texLocation = gl.getUniformLocation(program, 'tex'),
        valLocation = gl.getUniformLocation(program, 'val'),
        sizeLocation = gl.getUniformLocation(program, 'size'),
        isLocation = gl.getUniformLocation(program, 'imgSize'),
        last_size = -1,
        last_d = -1,
        last_array = null;

    canvas.style.position = 'absolute';
    gl.useProgram(program);

    function cal(x, y, d){
        return 0.5*Math.exp(-(x*x + y*y)/(d*d*2))/(Math.PI*d*d);
    }
    function calArr(size, d){
        var arr = [],
            len = Math.floor(size / 2),
            res = 0,
            sum = 0;
        for(var i = 0; i < size; i++){
            for(var j = 0; j < size; j++){
                res = cal(j - len, i - len, d);
                arr[i*size + j] = res;
                sum += res;
            }
        }
        for(var i = 0; i < arr.length; i++){
            arr[i] /= sum;
        }
        return arr;
    }
    //console.log(calArr(3, 1.5));
    return {
        blur : function(img, imgWidth, imgHeight, canvasWidth, canvasHeight, size, d, need, toWidth, toHeight){
            canvas.width = canvasWidth;
            canvas.height = canvasHeight;
            gl.viewport(0, 0, canvasWidth, canvasHeight);
            //gl.useProgram(program);
            
            var tex = null;
            if(!need){
                tex = GLI.createTex(gl, img, imgWidth, imgHeight);
            }else{
                canvas_for_change_size.width = toWidth;
                canvas_for_change_size.height = toHeight;
                ctx.drawImage(img, 0, 0, toWidth, toHeight);
                tex = GLI.createTex(gl, canvas_for_change_size, toWidth, toHeight);
            }

            gl.uniform1i(texLocation, 0);

            // cal
            if(size === last_size && d === last_d){

            }else{
                last_array = calArr(size, d);
            }
            var arr = last_array;
            
            gl.uniform1fv(valLocation, arr);
            gl.uniform1i(sizeLocation, size);
            gl.uniform2f(isLocation, imgWidth, imgHeight);


            gl.drawArrays(gl.TRIANGLE_STRIP, 0, 4);
            gl.deleteTexture(tex);

        },
        DOM : function(){
            return canvas;
        },
        glReady : true
    };
})(GLI);