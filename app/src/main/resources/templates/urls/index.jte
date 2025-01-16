@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.utils.NamedRoutes
@import hexlet.code.utils.DateFormatter

@param UrlsPage page

@template.layout.page(
page = page,
content = @`
    <h1>URLs list</h1>
    <div>
        <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>URL</th>
                <th>Creation date</th>
                <th>Last check</th>
                <th>Response code</th>
            </tr>
            </thead>
            <tbody>
            @for(var url : page.getUrls())
                <tr>
                    <td>${url.getId()}</td>
                    <td><a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a></td>
                    <td>${DateFormatter.format(url.getCreatedAt())}</td>

                    @if(page.getLastChecks().containsKey(url.getId()))
                        <td>${DateFormatter.format(page.getLastChecks().get(url.getId()).getCreatedAt())}</td>
                        <td>${page.getLastChecks().get(url.getId()).getStatusCode()}</td>
                    @else
                        <td>No data</td>
                        <td>---</td>
                    @endif
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
`
)