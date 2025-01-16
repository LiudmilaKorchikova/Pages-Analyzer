@import hexlet.code.dto.urls.MainPage
@import hexlet.code.utils.NamedRoutes

@param MainPage page

@template.layout.page(
    page = page,
    content = @`

        <h1>Pages Analyzer</h1>
        <p>Use this site to check your websites for SEO suitability.</p>
        <h2>Add URL</h2>
        <form action="${NamedRoutes.urlsPath()}" method="POST">
            <div class="form-group">
                <label for="url">Enter URL</label>
                <input type="text" class="form-control" id="url" name="url" placeholder="Enter URL" required>
            </div>
            <div>
            <button type="submit" class="btn btn-primary">Add</button>
            </div>
        </form>
    `
)