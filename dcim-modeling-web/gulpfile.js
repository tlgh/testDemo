var fs = require('fs');
var path = require('path');

var gulp = require('gulp');

// Load all gulp plugins automatically
// and attach them to the `plugins` object
var plugins = require('gulp-load-plugins')();

// Temporary solution until gulp 4
// https://github.com/gulpjs/gulp/issues/355
var runSequence = require('run-sequence');

var notify = require('gulp-notify');
var minifyCss = require('gulp-minify-css');
var uglify = require('gulp-uglify');
var pkg = require('./package.json');
var dirs = pkg['h5bp-configs'].directories;

// ---------------------------------------------------------------------
// | Helper tasks                                                      |
// ---------------------------------------------------------------------

gulp.task('archive:create_archive_dir', function () {
    fs.mkdirSync(path.resolve(dirs.archive), '0755');
});

gulp.task('archive:zip', function (done) {

    var archiveName = path.resolve(dirs.archive, pkg.name + '_v' + pkg.version + '.zip');
    var archiver = require('archiver')('zip');
    var files = require('glob').sync('**/*.*', {
        'cwd': dirs.dist,
        'dot': true // include hidden files
    });
    var output = fs.createWriteStream(archiveName);

    archiver.on('error', function (error) {
        done();
        throw error;
    });

    output.on('close', done);

    files.forEach(function (file) {

        var filePath = path.resolve(dirs.dist, file);

        // `archiver.bulk` does not maintain the file
        // permissions, so we need to add files individually
        archiver.append(fs.createReadStream(filePath), {
            'name': file,
            'mode': fs.statSync(filePath)
        });

    });

    archiver.pipe(output);
    archiver.finalize();

});

gulp.task('clean', function (done) {
    require('del')([
        dirs.archive,
        dirs.dist
    ], done);
});

gulp.task('minify-css', function () {
    return gulp.src(dirs.src + '/css/*.css')
        .pipe(minifyCss())
        .pipe(gulp.dest(dirs.dist + '/css'));
});
gulp.task('compress-js', [
    'compress-js:js',
    'compress-js:views',
    'compress-js:requirejs'
]);
gulp.task('compress-js:js', function () {
    return gulp.src([
        dirs.src + '/js/**',
        '!' + dirs.src + '/js/vendor/**'
    ]).pipe(uglify())
        .pipe(gulp.dest(dirs.dist + '/js'));
});
gulp.task('compress-js:views', function () {
    return gulp.src([
        dirs.src + '/views/**/*.js'
    ]).pipe(uglify())
        .pipe(gulp.dest(dirs.dist + '/views'));
});
gulp.task('compress-js:requirejs', function () {
    return gulp.src([
        dirs.src + '/js/vendor/require.js'
    ]).pipe(uglify())
        .pipe(gulp.dest(dirs.dist + '/js/vendor'));
});

gulp.task('copy', [
    'copy:.htaccess',
    'copy:license',
    'copy:misc',
    'copy:vendor'
]);

gulp.task('copy:.htaccess', function () {
    return gulp.src('node_modules/apache-server-configs/dist/.htaccess')
        .pipe(plugins.replace(/# ErrorDocument/g, 'ErrorDocument'))
        .pipe(gulp.dest(dirs.dist));
});

gulp.task('copy:license', function () {
    return gulp.src('LICENSE.txt')
        .pipe(gulp.dest(dirs.dist));
});


gulp.task('copy:misc', function () {
    return gulp.src([

        // Copy all files
        dirs.src + '/**/*',
        // Exclude the following files
        // (other tasks will handle the copying of these files)
        '!' + dirs.src + '/css/*',
        '!' + dirs.src + '/js/**',
        '!' + dirs.src + '/views/**/*.js'
    ], {

        // Include hidden files by default
        dot: true

    }).pipe(gulp.dest(dirs.dist));
});

gulp.task('copy:vendor', function () {
    return gulp.src([

        // Copy all vendor files
        dirs.src + '/js/vendor/**',
        '!' + dirs.src + '/js/vendor/require.js'
    ], {

        // Include hidden files by default
        dot: true

    }).pipe(gulp.dest(dirs.dist + '/js/vendor/'));
});


gulp.task('lint:js', function () {
    return gulp.src([
        'gulpfile.js',
        dirs.src + '/js/*.js',
        dirs.test + '/*.js'
    ]).pipe(plugins.jscs())
        .pipe(plugins.jshint())
        .pipe(plugins.jshint.reporter('jshint-stylish'))
        .pipe(plugins.jshint.reporter('fail'));
});


// ---------------------------------------------------------------------
// | Main tasks                                                        |
// ---------------------------------------------------------------------

gulp.task('archive', function (done) {
    runSequence(
        'build',
        'archive:create_archive_dir',
        'archive:zip',
        done);
});

gulp.task('build', function (done) {
    runSequence(
        ['clean'],
        'minify-css',
        'compress-js',
        'copy',
        done);
});

gulp.task('default', ['build']);

